import style from './modeSelect.module.css';
import Link from 'next/link';


export default function ModeSelect({toMain, toConfigure}) {
  
  return (
    <main className={style.container}>
      <Link href={'/game'} passHref>
        <h2>single mode</h2>
      </Link>
      <h2>multi mode</h2>
      <button onClick={toConfigure}>configure</button>
      <button onClick={toMain}>back</button>
    </main>
  )
}