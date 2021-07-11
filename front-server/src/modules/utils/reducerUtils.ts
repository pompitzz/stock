import { AsyncActionCreatorBuilder, getType } from 'typesafe-actions';
import { AnyAction } from 'redux';
import { AxiosError } from 'axios';

type AnyAsyncActionCreator =
  AsyncActionCreatorBuilder<[any, [any, undefined]], [any, [any, undefined]], [any, [any, undefined]]> |
  AsyncActionCreatorBuilder<[any, [undefined, undefined]], [any, [any, undefined]], [any, [any, undefined]]>
  ;

export type ApiState<T> = {
  data: T | null;
  loading: boolean;
  error: AxiosError | null;
};

export const apiState = {
  initial: <T>(initialData?: T): ApiState<T> => ({
    loading: false,
    data: initialData || null,
    error: null
  }),
  request: <T>(data?: T): ApiState<T> => ({
    loading: true,
    data: data || null,
    error: null
  }),
  success: <T>(data: T): ApiState<T> => ({
    loading: false,
    data,
    error: null
  }),
  failure: <T>(error: AxiosError): ApiState<T> => ({
    loading: false,
    data: null,
    error: error
  })
};

export function createApiReducer<S, AC extends AnyAsyncActionCreator, K extends keyof S>(
  asyncActionCreator: AC,
  key: K,
) {
  const REQUEST = getType(asyncActionCreator.request)
  const SUCCESS = getType(asyncActionCreator.success)
  const FAILURE = getType(asyncActionCreator.failure)
  return {
    [REQUEST]: (state: S) => ({
      ...state,
      [key]: apiState.request()
    }),
    [SUCCESS]: (state: S, action: AnyAction) => {
      // console.log('success action', action);
      return ({
        ...state,
        [key]: apiState.success(action.payload)
      });
    },
    [FAILURE]: (state: S, action: AnyAction) => {
      // console.log('failure action', action);
      return ({
        ...state,
        [key]: apiState.failure(action.payload)
      });
    },
  }
}
