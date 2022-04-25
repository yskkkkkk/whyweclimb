import Lobby from '../components/multi/lobby';
import { useState } from 'react';

export default function Multi() {
  
  const [multiRoom, setMultiRoom] = useState(0);

  const toLobby = () => {
    setMultiRoom(0);
  }

  return (
    <main>
      {multiRoom == 0 && <Lobby />}
      
      
    </main>
  )
}