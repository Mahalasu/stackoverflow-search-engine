import React from 'react'
import { Typography } from '@mui/material'

import useStyles from './styles'
import logo from '../../image/logo.png'
import Search from '../Search/Search'

const MainPage = () => {
  const classes = useStyles()

  return (
    <div className={classes.div}>
      <div>
        <img src={logo} className={classes.logo} alt="logo" />
      </div>
      <Typography variant="h1">Stackoverflow Search</Typography>
      <Search className={classes.search} keyword="" />
    </div>
  )
}

export default MainPage
