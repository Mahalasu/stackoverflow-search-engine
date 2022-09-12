import React, { useState, useEffect } from 'react'
import { TextField, Autocomplete, Stack } from '@mui/material'
import { Search as SearchIcon } from '@mui/icons-material'
import { useHistory } from 'react-router-dom'

const Search = ({ className, keyword }) => {
  const [query, setQuery] = useState(keyword)
  const [topRelativeSegments, setTopRelativeSegments] = useState([])
  const history = useHistory()

  useEffect(() => {
    fetch(`http://127.0.0.1:8081/relative_segments?word=${query}`)
      .then((resp) => resp.json())
      .then((json) => {
        if (query === '') setTopRelativeSegments([])
        else setTopRelativeSegments(json)
      })
  }, [query])

  const enterKeyPress = (e) => {
    if (e.key === 'Enter') {
      const encodedQuery = encodeURIComponent(query)
      history.push(`/search?keyword=${encodedQuery}&pageNum=1`)
    }
  }

  return (
    <Stack className={className}>
      <Autocomplete
        inputValue={query}
        onChange={(e, value) => setQuery(value)}
        onInputChange={(e) => {
          if (typeof e.target.value === Number)
            setQuery(topRelativeSegments[e.target.value])
          else setQuery(e.target.value)
        }}
        freeSolo
        id="free-solo-2-demo"
        disableClearable
        options={topRelativeSegments}
        onKeyPress={enterKeyPress}
        renderInput={(params) => (
          <TextField
            {...params}
            label="Search"
            InputProps={{
              ...params.InputProps,
              type: 'search',
              startAdornment: <SearchIcon />,
            }}
          />
        )}
      />
    </Stack>
  )
}

export default Search
