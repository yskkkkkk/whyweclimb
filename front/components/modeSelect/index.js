import style from './modeSelect.module.css';
import Link from 'next/link';


export default function ModeSelect({toMultiLobby, toHome}) {
  
  return (
    <main className={style.container}>
      <Link href={'/game'} passHref>
        <h2>single mode</h2>
      </Link>
      <h2 onClick={toMultiLobby}>multi mode</h2>
      <button onClick={toHome}>back</button>
    </main>
  )
}