import Lobby from '../components/multi/lobby';
import style from '../styles/multi.module.css';
import Link from 'next/link';
import { useState } from 'react';

export default function Multi() {
  
  const [multiRoom, setMultiRoom] = useState(0);

  const toLobby = () => {
    setMultiRoom(0);
  }

  return (
    <main className={style.multi}>
      {multiRoom == 0 && <Lobby />}

      <Link href={'/'} passHref>
        <button>back</button>
      </Link>        
    </main>
  )
}