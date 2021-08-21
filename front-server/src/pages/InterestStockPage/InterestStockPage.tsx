import React from 'react';
import { withRouter } from 'react-router-dom';
import InterestStockEditor from './InterestStockEditor';

function InterestStockPage() {
  return (
    <div>
      <InterestStockEditor />
    </div>
  );
}

export default withRouter(InterestStockPage);
