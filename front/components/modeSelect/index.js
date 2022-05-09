import style from './modeSelect.module.css';
import Link from 'next/link';
import { useSpring, animated } from 'react-spring'
import { useEffect } from 'react';


export default function ModeSelect({toMain, toConfigure}) {
  
  // const test = (e) => {
  //   e.preventDefault();

  //   const token = localStorage.getItem("token");
  //   console.log(token);
  //   const headers = {
  //     'Authorization': token,
  //     mode: 'no-cors'
  //   }
  //   fetch('https://k6a401.p.ssafy.io/api/user/information', {headers: headers})
  //   .then((response) => {
  //     console.log(response.json());
  //   });
  // };

  const [styles, api] = useSpring(() => ({
    from: { y: -50, opacity: 1 },
  }))

  useEffect(() => {
    api({
      y: 50,
      opacity: 1,
      loop: { reverse: true },
    })
  }, [])

  return (
    <main className={style.container}>
      {/* <Link href={'/single/singleGame'} passHref> */}
      <a href={'/single/singleGame'}>
        <h2>single mode</h2>
      </a>
      {/* </Link> */}
      <Link href={'/multi'} passHref>
        <h2>multi mode</h2>
      </Link>
      {/* <button onClick={toConfigure}>configure</button> */}
      <button className={style.btn} onClick={toMain}>logout</button>
      <animated.div
      style={{
        
        width: 80,
        height: 80,
        backgroundColor: '#46e891',
        borderRadius: 16,
        ...styles,
        }}
      />
    </main>
  )
}