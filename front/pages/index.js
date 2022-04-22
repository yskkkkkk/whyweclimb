import Head from 'next/head';
import Login from "../components/login"
import Signup from '../components/signup';
import { useState } from 'react';

export default function Home() {
  
  const [showLogin, setShowLogin] = useState(true);
  const [showSignup, setShowSignup] = useState(false);  

  const showSignupToggle = () => {
    setShowSignup(!showSignup);
    setShowLogin(!showLogin);
  }

  return (
    <>
      <Head>
        <meta charSet="utf-8" />
        <meta name="viewport" content="width=device-width" />
        <title>why-we-climb</title>
        {/* <script type="text/javascript" src="../components/main.js"></script> */}
      </Head>

      <main className="intro">
        <section>
          <h2>why we climb</h2>
        </section>
        <section>
          {showLogin && <Login toSignup={showSignupToggle} />}
          {showSignup && <Signup toLogin={showSignupToggle} />}
        </section>
      </main>

      
      
      
      
    </>
  )
}
