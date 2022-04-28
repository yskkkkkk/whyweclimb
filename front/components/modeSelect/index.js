import style from './modeSelect.module.css';
import Link from 'next/link';


export default function ModeSelect({toMain, toConfigure}) {
  
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
    </main>
  )
}