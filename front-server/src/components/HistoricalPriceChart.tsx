import React, { useEffect, useRef } from 'react';
import { HistoricalPrice, PeriodType } from '../types/stock';
import { makeStyles, Theme } from '@material-ui/core/styles';
import { Chart } from 'chart.js';


const useStyles = makeStyles((theme: Theme) => ({}));

interface ChartExamplesProps {
  historicalPrices: HistoricalPrice[];
  currentPeriodType: PeriodType;
}

function HistoricalPriceChart({ historicalPrices, currentPeriodType }: ChartExamplesProps) {
  const chart = useRef<HTMLCanvasElement>(null);
  useEffect(() => {
    if (!chart.current) {
      return;
    }
    const historicalPriceChart = new Chart(
      chart.current,
      {
        type: 'line',
        data: {
          datasets: [
            {
              label: currentPeriodType.desc,
              backgroundColor: 'rgb(255, 99, 132)',
              borderColor: 'rgb(255, 99, 132)',
              data: historicalPrices.map(({ price, date }) => ({ x: date, y: price })),
            },
          ]
        }
      });
    return () => {
      historicalPriceChart.destroy();
    }
  });

  const classes = useStyles();
  return (
    <div>
      <canvas ref={chart} />
    </div>
  );
}

export default HistoricalPriceChart;
