import StockSearchPage from '../pages/SearchStockPage/StockSearchPage';
import { Search } from '@material-ui/icons';
import StockDetailPage from '../pages/StockDetailPage';

export type RouteContext = {
  name: string;
  path: string;
  pageComponent: JSX.Element;
  showInMenu: boolean;
  menuIconComponent?: JSX.Element;
}

export const routes: RouteContext[] = [
  {
    name: 'Search Stock',
    path: '/search-stock',
    pageComponent: <StockSearchPage />,
    showInMenu: true,
    menuIconComponent: <Search />,
  },
  {
    name: 'Stock Detail',
    path: '/stock-detail/:symbol',
    pageComponent: <StockDetailPage />,
    showInMenu: false,
  }
]
