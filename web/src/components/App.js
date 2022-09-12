import React from 'react'
import { Container } from '@mui/material'
import { Route, Switch } from 'react-router-dom'

import ResultDisplay from './ResultDisplay/ResultDisplay'
import MainPage from './MainPage/MainPage'

const App = () => {
  return (
    <Container>
      <Switch>
        <Route path="/" exact>
          <MainPage />
        </Route>
        <Route path="/search">
          <ResultDisplay />
        </Route>
      </Switch>
    </Container>
  )
}

export default App
