import React from 'react';
import { PeriodType } from '../types/stock';
import { Chip } from '@material-ui/core';
import { makeStyles, Theme } from '@material-ui/core/styles';

const useStyles = makeStyles((theme: Theme) => ({
  root: {
    textAlign: 'center',
    '& > *': {
      margin: theme.spacing(0.5)
    },
    padding: theme.spacing(1)
  },
}));

interface PeriodSelectProps {
  selectPeriodType: (periodType: PeriodType) => void;
  currentPeriodType: PeriodType;
}

function PeriodSelect({ selectPeriodType, currentPeriodType }: PeriodSelectProps) {
  const classes = useStyles();
  return (
    <div className={classes.root}>
      {
        PeriodType.TYPES.map(periodType =>
          <Chip
            label={periodType.desc}
            key={periodType.name}
            onClick={() => selectPeriodType(periodType)}
            color={currentPeriodType.name === periodType.name ? 'primary' : 'default'}
          />)
      }
    </div>
  );
}

export default PeriodSelect;
