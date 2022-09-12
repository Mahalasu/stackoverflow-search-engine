import { createApi, fetchBaseQuery } from '@reduxjs/toolkit/query/react'

export const searchApi = createApi({
  reducerPath: 'searchApi',
  baseQuery: fetchBaseQuery({ baseUrl: 'http://127.0.0.1:8081' }),
  endpoints: (builder) => ({
    getResults: builder.query({
      query: ({ keyword, pageNum }) =>
        `/search?keyword=${keyword}&pageNum=${pageNum}`,
    }),
  }),
})

export const { useGetResultsQuery } = searchApi
