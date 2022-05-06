import style from './modeSelect.module.css';
import Link from 'next/link';


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
    </main>
  )
}