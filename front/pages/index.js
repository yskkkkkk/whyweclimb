import Head from 'next/head';
import Login from "../components/login"
import Signup from '../components/signup';
import ModeSelect from '../components/modeSelect';
import { useState, useEffect, useRef } from 'react';
import Configure from '../components/configure';
import { Parallax, ParallaxLayer } from '@react-spring/parallax';



export default function Home() {

  const mainRef = useRef();
  const inRef = useRef();
  const [loggedIn, setLoggedIn] = useState(false);    // false: 로그인 & 회원가입 컴포넌트, true: 모드선택 & 설정 컴포넌트

  const toMain = () => {
    setLoggedIn(false);
    window.localStorage.clear();
    mainRef.current.scrollTo(0);
    inRef.current.scrollTo(3);
  }
  const toSignup = () => {
    setLoggedIn(false);
    mainRef.current.scrollTo(1);
  }
  const toModeSelect = () => {
    setLoggedIn(true);
    inRef.current.scrollTo(0);
  }
  const toConfigure = () => {
    setLoggedIn(true);
  }

  useEffect(() => {             // 로그인 여부에 따라 메인화면 바뀜
    setLoggedIn(localStorage.getItem("token") ? true : false);
  }, []);

  useEffect(() => {
    console.log(loggedIn);
  }, [loggedIn])

  return (
    <>
      <Head>
        <meta charSet="utf-8" />
        <meta name="viewport" content="width=device-width" />
        <title>why-we-climb</title>
        {/* <script type="text/javascript" src="../components/main.js"></script> */}
      </Head>

      <main>
        <Parallax 
        ref={inRef} 
        pages={4}
        style={{
          overflow: "hidden",
        }}
        >
          <ParallaxLayer offset={0}>
            {loggedIn && <ModeSelect toMain={toMain} toConfigure={toConfigure} />}
          </ParallaxLayer>
          <ParallaxLayer offset={1}>
            <h2>you ready???</h2>
          </ParallaxLayer>
          <ParallaxLayer offset={2}>
            <h2>you ready?</h2>
          </ParallaxLayer>
          <ParallaxLayer offset={3}>
            <Parallax 
              pages={2}
              ref={mainRef}
              horizontal
              style={{
                overflow: "hidden",
              }}
            >
              <ParallaxLayer offset={1}>
                {!loggedIn && <Signup toMain={toMain} />}
              </ParallaxLayer>
              <ParallaxLayer offset={0}>
                {!loggedIn && <Login toSignup={toSignup} toModeSelect={toModeSelect} />}
              </ParallaxLayer>
            </Parallax>
          </ParallaxLayer>
        </Parallax>
        {/* {loggedIn && <ModeSelect toMain={toMain} toConfigure={toConfigure} />} */}
        {/* {loggedIn && <Configure toModeSelect={toModeSelect} />} */}
      </main>
    </>
  )
}
