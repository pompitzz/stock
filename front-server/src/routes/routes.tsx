import StockSearchPage from '../pages/SearchStockPage/StockSearchPage';
import { Search, Star } from '@material-ui/icons';
import StockDetailPage from '../pages/StockDetailPage';
import AuthenticationPage from '../pages/AuthenticationPage';
import InterestStockPage from '../pages/InterestStockPage/InterestStockPage';

export type RouteContext = {
  name: string;
  path: string;
  pageComponent: JSX.Element;
  showInMenuOption: ShowInMenuOption;
  menuIconComponent?: JSX.Element;
}

export enum ShowInMenuOption {
  ALWAYS = 'ALWAYS',
  LOGGED_IN = 'LOGGED_IN',
  NEVER = 'NEVER',
}

export const routes: RouteContext[] = [
  {
    name: 'Search Stock',
    path: '/search-stock',
    pageComponent: <StockSearchPage />,
    showInMenuOption: ShowInMenuOption.ALWAYS,
    menuIconComponent: <Search />,
  },
  {
    name: 'Stock Detail',
    path: '/stock-detail/:symbol',
    pageComponent: <StockDetailPage />,
    showInMenuOption: ShowInMenuOption.NEVER,
  },
  {
    name: '',
    path: '/login',
    pageComponent: <AuthenticationPage />,
    showInMenuOption: ShowInMenuOption.NEVER
  },
  {
    name: 'Interest Stock',
    path: '/interest-stock',
    pageComponent: <InterestStockPage />,
    showInMenuOption: ShowInMenuOption.LOGGED_IN,
    menuIconComponent: <Star />
  }
]
