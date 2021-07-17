import React, { useEffect, useRef } from 'react';
import { HistoricalPrice } from '../types/stock';
import { makeStyles, Theme } from '@material-ui/core/styles';
import { Chart } from 'chart.js';
import { useTheme } from '@material-ui/core';


const useStyles = makeStyles((theme: Theme) => ({}));

interface HistoricalPriceChartProps {
  historicalPrices: HistoricalPrice[];
  detailChart: boolean;
}

function HistoricalPriceChart({ historicalPrices, detailChart }: HistoricalPriceChartProps) {
  const chart = useRef<HTMLCanvasElement>(null);
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
              backgroundColor: theme.palette.info.dark,
              borderColor: theme.palette.info.main,
              data: historicalPrices.map(({ close, date }) => ({ x: date, y: close })),
            },
          ]
        },
        options: {
          responsive: true,
          // maintainAspectRatio: false,
          plugins: {
            legend: {
              display: false,
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

HistoricalPriceChart.defaultProps = {
  detailChart: false,
}

export default HistoricalPriceChart;
