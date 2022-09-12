import React from 'react'
import {
  Typography,
  CircularProgress,
  Box,
  Card,
  CardContent,
  CardActions,
  Chip,
  Pagination,
  Stack,
} from '@mui/material'
import { ThumbUp, Visibility } from '@mui/icons-material'
import { useHistory, useLocation } from 'react-router-dom'

import { useGetResultsQuery } from '../../api'
import smallLogo from '../../image/small_logo.png'
import useStyles from './styles'
import Search from '../Search/Search'

const ResultDisplay = () => {
  const prefix = 'https://stackoverflow.com'
  const { search } = useLocation()
  const params = new URLSearchParams(search)
  const keyword = params.get('keyword')
  const pageNum = params.get('pageNum')
  const { data, error, isFetching } = useGetResultsQuery({
    keyword: encodeURIComponent(keyword),
    pageNum,
  })
  const classes = useStyles()
  const history = useHistory()

  if (isFetching) {
    return (
      <Box display="flex" justifyContent="center">
        <CircularProgress size="4rem" />
      </Box>
    )
  }

  if (!data) {
    return (
      <Box display="flex" alignItems="center" mt="20px">
        <Typography variant="h4">
          No Results match.
          <br />
          Please search for something else.
        </Typography>
      </Box>
    )
  }

  if (error) return 'An error has occured'

  return (
    <>
      <div>
        <a href="http://127.0.0.1:3000">
          <img className={classes.logo} src={smallLogo} alt="logo" />
        </a>
        <Search keyword={keyword} className={classes.search} />
      </div>
      {data._1.map((d, idx) => (
        <Card key={idx} variant="outlined" className={classes.card}>
          <CardContent>
            <Typography gutterBottom>
              <a
                className={classes.link}
                href={prefix + d.url}
                target="_blank"
                rel="noreferrer"
              >
                {d.caption}
              </a>
            </Typography>
          </CardContent>
          <CardActions>
            <Chip icon={<Visibility />} label={' ' + d.views + ' views'} />
            <Chip icon={<ThumbUp />} label={' ' + d.votes + ' votes'} />
          </CardActions>
        </Card>
      ))}
      <div className={classes.pagination}>
        <Stack spacing={2}>
          <Pagination
            page={parseInt(pageNum)}
            count={data._2}
            color="primary"
            shape="rounded"
            siblingCount={2}
            onChange={(e, page) =>
              history.push(
                `/search?keyword=${encodeURIComponent(
                  keyword,
                )}&pageNum=${page}`,
              )
            }
          />
        </Stack>
      </div>
    </>
  )
}

export default ResultDisplay
