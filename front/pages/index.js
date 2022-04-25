import Head from 'next/head';
import Login from "../components/login"
import Signup from '../components/signup';
import ModeSelect from '../components/modeSelect';
import { useState } from 'react';
import Configure from '../components/configure';



export default function Home() {

  const [room, setRoom] = useState(0);    // 0=메인(로그인), 1=회원가입, 2=모드 선택화면, 3=설정화면,

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
          {room == 0 && <Login toSignup={toSignup} toModeSelect={toModeSelect} />}
          {room == 1 && <Signup toMain={toMain} />}
          {room == 2 && <ModeSelect toMain={toMain} toConfigure={toConfigure} />}
          {room == 3 && <Configure toModeSelect={toModeSelect} />}
        </section>
      </main>
    </>
  )
}
