import Head from 'next/head';
import Login from "../components/login"
import Signup from '../components/signup';
import ModeSelect from '../components/modeSelect';
import { useState, useEffect } from 'react';
import Configure from '../components/configure';



export default function Home() {

  const [room, setRoom] = useState(false);    // 0=메인(로그인), 1=회원가입, 2=모드 선택화면, 3=설정화면,

  const toMain = () => {
    setRoom(0);
  }
  const toSignup = () => {
    setRoom(1);
  }
  const toModeSelect = () => {
    setRoom(2);
  }
  const toConfigure = () => {
    setRoom(3);
  }

  useEffect(() => {             // 로그인 여부에 따라 메인화면 바뀜
    setRoom(localStorage.getItem("token") ? 2 : 0);
  }, []);


  return (
    <>
      <Head>
        <meta charSet="utf-8" />
        <meta name="viewport" content="width=device-width" />
        <title>why-we-climb</title>
        
        {/* <script type="text/javascript" src="../components/main.js"></script> */}
      </Head>

      <main className="intro">
        <header>
          <h2>why we climb</h2>
          <div className = "image-box">
          <img className = "intro-image" src="images/intro.jpg"></img>
          </div>
        </header>
        <section>
          {room == 0 && <Login toSignup={toSignup} toModeSelect={toModeSelect} />}
          {room == 1 && <Signup toMain={toMain} />}
          {room == 2 && <ModeSelect toMain={toMain} toConfigure={toConfigure} />}
          {room == 3 && <Configure toModeSelect={toModeSelect} />}
        </section>
      </main>
    </>
  )
}
