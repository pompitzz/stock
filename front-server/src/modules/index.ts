import { combineReducers } from 'redux';
import { all } from 'redux-saga/effects'
import stock, { stockSaga } from './stock';

const rootReducer = combineReducers({
  stock,
});

export type RootState = ReturnType<typeof rootReducer>;

export default rootReducer;

export function* rootSaga() {
  yield all([stockSaga()])
}
