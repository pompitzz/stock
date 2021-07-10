import { call, put } from 'redux-saga/effects';
import { AsyncActionCreatorBuilder, PayloadAction } from 'typesafe-actions';

export const createRequestActionTypes = (type: string) => [type, `${type}_SUCCESS`, `${type}_FAILURE`];

/**
 * __ is __ is Type guard.
 * {@see https://www.typescriptlang.org/docs/handbook/advanced-types.html#type-guards-and-type-assertions}
 */
function hasPayloadProperty<P>(action: any): action is PayloadAction<string, P> {
  return action.payload !== undefined;
}

export default function createAsyncSaga<P1, P2, P3>(
  asyncActionCreator: AsyncActionCreatorBuilder<[string, [unknown extends P1 ? undefined : P1, undefined]], [string, [P2, undefined]], [string, [P3, undefined]]>,
  promiseCreator: ((payload: P1) => Promise<P2>) | (() => Promise<P2>)
) {
  return function* saga(action: ReturnType<typeof asyncActionCreator.request>) {
    try {
      const response = hasPayloadProperty<P1>(action)
        // @ts-ignore
        ? yield call(promiseCreator, action.payload)
        // @ts-ignore
        : yield call(promiseCreator);
      yield put(asyncActionCreator.success(response));
    } catch (e) {
      yield put(asyncActionCreator.failure(e));
    }
  };
}
