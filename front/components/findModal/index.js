import {useRouter} from 'next/router';
import { useState, useEffect } from 'react';
import style from './findModal.module.css';

export default function FindModal({toggleFindModal}) {

  const router = useRouter();
  const [roomID, setRoomID] = useState('');

  const writeRoomID = (e) => {
    setRoomID(e.target.value);
  };

  // const findRoom = () => {
  //   fetch(`https://${userId}/exists`)
  //     .then((response) => response.json())
  //     .then((data) => {
  //       if (data) {
  //         router.push(`/${roomID}`);
  //       }
  //       else {
  //         alert('invalid RoomID');
  //       }
  //     });
  // };

  return (
    <section onClick={e => e.stopPropagation()} className={style.modal}>
      <div className={style.card}>
        <label>search room 
          <input type="text" required onChange={writeRoomID} /></label>
      </div>
      <div className={style.btns}>
        <button /*onClick={findRoom}*/ >join</button>
        <button onClick={toggleFindModal} >close</button>
      </div>
    </section>
  )
}