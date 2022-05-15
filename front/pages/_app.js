import '../styles/globals.css'
import { Toaster } from 'react-hot-toast';

function MyApp({ Component, pageProps }) {
  return (
    <>
      <Component {...pageProps} />
      <Toaster
        position="top-center" 
        // containerStyle={{
        //   position: 'absolute',
        //   top: '50%',
        // }}
        toastOptions={{
          blank: {
            duration: Infinity,
          },
        }}
      />
      
    </>
  )
}

export default MyApp
