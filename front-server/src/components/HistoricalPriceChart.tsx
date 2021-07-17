import React, { useEffect, useRef } from 'react';
import { HistoricalPrice, PeriodType } from '../types/stock';
import { makeStyles, Theme } from '@material-ui/core/styles';
import { Chart } from 'chart.js';
import { useTheme } from '@material-ui/core';


const useStyles = makeStyles((theme: Theme) => ({}));

interface ChartExamplesProps {
  historicalPrices: HistoricalPrice[];
  currentPeriodType?: PeriodType;
}

function HistoricalPriceChart({ historicalPrices, currentPeriodType }: ChartExamplesProps) {
  const chart = useRef<HTMLCanvasElement>(null);
  const detailChart = !!currentPeriodType;
  const theme = useTheme();
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
              label: currentPeriodType?.desc,
              backgroundColor: theme.palette.info.dark,
              borderColor: theme.palette.info.main,
              data: historicalPrices.map(({ price, date }) => ({ x: date, y: price })),
            },
          ]
        },
        options: {
          responsive: true,
          // maintainAspectRatio: false,
          plugins: {
            legend: {
              display: detailChart
            },
          },
          elements: {
            point: {
              radius: detailChart ? 3 : 0,
            }
          },
          scales: {
            y: {
              display: detailChart
            },
            x: {
              display: detailChart
            }
          }
        }
      });
    chart.current.style.height = '100%'
    return () => {
      historicalPriceChart.destroy();
    }
  });

  const classes = useStyles();
  return (
    <canvas ref={chart} style={{ height: '100%' }} />
  );
}

export default HistoricalPriceChart;
