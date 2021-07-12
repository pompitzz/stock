import React, { useEffect, useRef } from 'react';
import { Chart, registerables } from 'chart.js';

function ChartExamples(){
  const chart1 = useRef<HTMLCanvasElement>(null);
  const chart2 = useRef<HTMLCanvasElement>(null);
  Chart.register(...registerables);
  useEffect(() => {
    if (!chart1.current) {
      return;
    }
    const myChart = new Chart(
      chart1.current,
      {
        type: 'line',
        data: {
          labels: [
            'January',
            'February',
            'March',
            'April',
            'May',
            'June',
            'test'
          ],
          datasets: [
            {
              label: 'My First dataset',
              backgroundColor: 'rgb(255, 99, 132)',
              borderColor: 'rgb(255, 99, 132)',
              data: [0, 10, 5, 2, 20, 30, 45],
            },
            {
              label: 'My Second dataset',
              backgroundColor: 'rgb(255, 99, 132)',
              borderColor: 'rgb(255, 99, 132)',
              data: [0, 3, 10, 33, 11, 30, 45],
            }
          ]
        }
      });
    if (!chart2.current) {
      return;
    }
    new Chart(chart2.current, {
      type: 'bar',
      data: {
        labels: ['Red', 'Blue', 'Yellow', 'Green', 'Purple', 'Orange'],
        datasets: [{
          label: '# of Votes',
          data: [12, 19, 3, 5, 2, 3],
          backgroundColor: [
            'rgba(255, 99, 132, 0.2)',
            'rgba(54, 162, 235, 0.2)',
            'rgba(255, 206, 86, 0.2)',
            'rgba(75, 192, 192, 0.2)',
            'rgba(153, 102, 255, 0.2)',
            'rgba(255, 159, 64, 0.2)'
          ],
          borderColor: [
            'rgba(255, 99, 132, 1)',
            'rgba(54, 162, 235, 1)',
            'rgba(255, 206, 86, 1)',
            'rgba(75, 192, 192, 1)',
            'rgba(153, 102, 255, 1)',
            'rgba(255, 159, 64, 1)'
          ],
          borderWidth: 1
        }]
      },
      options: {
        scales: {
          y: {
            beginAtZero: true
          }
        }
      }
    });
  });
  return (
    <div>
      <canvas ref={chart1} />
      <canvas ref={chart2} />
    </div>
  );
}

export default ChartExamples;
