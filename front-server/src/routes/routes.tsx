import SearchStockPage from '../pages/SearchStockPage';
import { Search } from '@material-ui/icons';

export type RouteContext = {
  name: string;
  path: string;
  iconComponent: JSX.Element;
  pageComponent: JSX.Element;
}

export const routes: RouteContext[] = [
  {
    name: 'Search Stock',
    path: '/search-stock',
    iconComponent: <Search />,
    pageComponent: <SearchStockPage />
  }
]
