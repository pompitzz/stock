import { Page, ServerPageResponse } from '../../types/pages';
import { plainToClass } from 'class-transformer';

const apiResponseConverter = {
  convertPage<T>(data: ServerPageResponse<T>): Page<T> {
    return plainToClass(ServerPageResponse, data).toPage() as Page<T>;
  }
}

export default apiResponseConverter;
