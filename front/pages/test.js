import Login from "../components/login"
import Signup from '../components/signup';
import { useState, useEffect, useRef } from 'react';
import { Parallax, ParallaxLayer } from '@react-spring/parallax';

export default function Test() {
  
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

  return (
    <main>
      <Parallax 
        pages={2}
        ref={ref}
        horizontal
        style={{
          overflow: "hidden",
        }}
      >
        <ParallaxLayer offset={0} speed={1}>
          {!loggedIn && <Login toSignup={toSignup} />}
        </ParallaxLayer>
        <ParallaxLayer offset={1} speed={1}>
          {!loggedIn && <Signup toMain={toMain} />}
        </ParallaxLayer>
      </Parallax>
    </main>
  )
}