import { createAsyncAction } from 'typesafe-actions';

export function createApiAction(request: string) {
  return createAsyncAction(request, request + '_SUCCESS', request + '_FAILURE');
}
