import '../styles/globals.css'
import { Toaster } from 'react-hot-toast';
import {motion} from 'framer-motion';
import { LanguageProvider } from '../context/LanguageContext';


function MyApp({ Component, pageProps, router }) {

  return (
    <LanguageProvider>
      <motion.div
        key={router.route}
        initial="initial"
        animate="animate"
        variants={{
          initial: {
            opacity: 0,
          },
          animate: {
            opacity: 1,
          },
        }}>
        <Component {...pageProps} />
      </motion.div>
      <Toaster
        position="top-center" 
        // containerStyle={{
        //   position: 'absolute',
        //   top: '50%',
        // }}
        // toastOptions={{
        //   blank: {
        //     duration: Infinity,
        //   },
        // }}
      />
      
    </LanguageProvider>
  )
}

export default MyApp
