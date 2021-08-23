import { combineReducers } from 'redux';
import { all } from 'redux-saga/effects'
import stock, { stockSaga } from './stock';
import auth, { authSaga } from './auth';
import user, { userSaga } from './user';

const rootReducer = combineReducers({
  stock,
  auth,
  user
});

export type RootState = ReturnType<typeof rootReducer>;

export default rootReducer;

export function* rootSaga() {
  yield all([stockSaga(), authSaga(), userSaga()])
}
