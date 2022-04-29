import style from './modeSelect.module.css';
import Link from 'next/link';


export default function ModeSelect({toMain, toConfigure}) {
  
  const test = (e) => {
    e.preventDefault();

    const token = localStorage.getItem("token");
    console.log(token);
    const headers = {
      'Authorization': token,
      mode: 'no-cors'
    }
    fetch('http://k6a401.p.ssafy.io:8081/api/user/information', {headers: headers})
    .then((response) => {
      console.log(response.json());
    });
    // fetch('https://api.themoviedb.org/3/movie/popular?api_key=cbd74fe726ff642366194acd512090b7&language=en-US&page=1')
    // .then((response) => {
    //   // console.log(response.json());
    //   return response.json();
    // })
    // .then((data) => {
    //   console.log(data);
    // })
    // preflight
  };


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
      <button onClick={toConfigure}>configure</button>
      <button onClick={toMain}>back</button>
      <button onClick={test} >check</button>
    </main>
  )
}