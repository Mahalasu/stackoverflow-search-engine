import { createApi, fetchBaseQuery } from '@reduxjs/toolkit/query/react'

export const searchApi = createApi({
  reducerPath: 'searchApi',
  baseQuery: fetchBaseQuery({
    baseUrl: 'https://app3233.acapp.acwing.com.cn/api',
  }),
  endpoints: (builder) => ({
    getResults: builder.query({
      query: ({ keyword, pageNum }) =>
        `/search?keyword=${keyword}&pageNum=${pageNum}`,
    }),
  }),
})

export const { useGetResultsQuery } = searchApi
