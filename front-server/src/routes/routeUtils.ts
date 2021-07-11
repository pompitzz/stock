import { RouteContext } from './routes';
import { Pathname } from 'history';

const routeUtils = {
  isMatch(routeContext: RouteContext, currentPathname: Pathname): boolean {
    const routeContextPath = routeContext.path;
    // TODO: consider path variable path
    return routeContextPath === currentPathname;
  }
}

export default routeUtils;
