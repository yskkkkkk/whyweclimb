import Head from 'next/head';
import Login from "../components/login"
import Signup from '../components/signup';
import ModeSelect from '../components/modeSelect';
import { useState, useEffect, useRef } from 'react';
import Configure from '../components/configure';
import { Parallax, ParallaxLayer } from '@react-spring/parallax';



export default function Home() {

  const ref = useRef();
  const [loggedIn, setLoggedIn] = useState(false);    // false: 로그인 & 회원가입 컴포넌트, true: 모드선택 & 설정 컴포넌트

  const toMain = () => {
    setLoggedIn(false);
    window.localStorage.clear();
    ref.current.scrollTo(0);
  }
  const toSignup = () => {
    setLoggedIn(false);
    ref.current.scrollTo(1);
  }
  const toModeSelect = () => {
    setLoggedIn(true);
  }
  const toConfigure = () => {
    setLoggedIn(true);
  }

  useEffect(() => {             // 로그인 여부에 따라 메인화면 바뀜
    setLoggedIn(localStorage.getItem("token") ? true : false);
  }, []);

  return (
    <>
      <Head>
        <meta charSet="utf-8" />
        <meta name="viewport" content="width=device-width" />
        <title>why-we-climb</title>
        <script type="text/javascript" src="../components/main.js"></script>
      </Head>

      <main>
        <Parallax 
          pages={2}
          ref={ref}
          horizontal
          style={{
            overflow: "hidden",
          }}
        >
          <ParallaxLayer offset={1}>
            {!loggedIn && <Signup toMain={toMain} />}
          </ParallaxLayer>
          <ParallaxLayer 
          offset={0}
          style={{
            backgroundImage: `url("/images/intro.jpg")`,
            backgroundSize: 1100,
            backgroundPositionX: "center",
            backgroundPositionY: "bottom",
          }}
          >
            {!loggedIn && <Login toSignup={toSignup} toModeSelect={toModeSelect} />}
          </ParallaxLayer>
        </Parallax>
        {loggedIn && <ModeSelect toMain={toMain} toConfigure={toConfigure} />}
        {loggedIn && <Configure toModeSelect={toModeSelect} />}
      </main>
    </>
  )
}
