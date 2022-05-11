import { useState } from 'react';
import style from './findModal.module.css';
import axios from 'axios'
import toast from 'react-hot-toast';


export default function FindModal() {
  const basicURL = 'https://k6a401.p.ssafy.io/api'
  const [roomID, setRoomID] = useState('');

  const writeRoomID = (e) => {
    setRoomID(e.target.value);
  };

  const findRoom = () => {
    axios.get(`${basicURL}/chat/room/${roomID}`)
      .then(res=>res.data)
      .then(data=>{
        if(data !== ""){
          location.href=`/multi/${data.roomCode}`;
        } else {
          alert("해당하는 방이 존재하지 않습니다. 다시 한번 확인해 주세요.");
        }
      })
      .catch(err=>console.error(err))
  }

  const closeModal = () => {
    toast.dismiss();
  }

  return (
    <section className={style.modal}>
      <h2>Find room</h2>
      <div className={style.card}>
        <label>search room 
          <input type="text" required onChange={writeRoomID} /></label>
      </div>
      <div className={style.btns}>
        <button onClick={findRoom} >join</button>
        <button onClick={closeModal} >close</button>
      </div>
    </section>
  )
}