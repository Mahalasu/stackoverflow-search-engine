import { configureStore } from '@reduxjs/toolkit'
import { searchApi } from '../api'

export default configureStore({
  reducer: {
    [searchApi.reducerPath]: searchApi.reducer,
  },
})
