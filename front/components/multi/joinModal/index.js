import {useRouter} from 'next/router';
import { useState, useEffect } from 'react';
import style from './joinModal.module.css';
import axios from 'axios'
import toast from 'react-hot-toast';

export default function JoinModal() {
  const basicURL = 'https://k6a401.p.ssafy.io/api'
  const [isInterference, setIsInterference] = useState("");

  const handleChange = (e) => {
    setIsInterference(e.target.value);
  }

  const joinRoom = () => {
    axios.get(`${basicURL}/chat/room?roomInterference=${isInterference}`)
      .then(res=>res.data)
      .then(data=>{
        if(data.length === 0) {
          alert("대기 중인 방이 없습니다. 잠시후 시도해 주세요.");
        } else {
          location.href=`multi/${data.roomCode}`;
        }
      })
      .catch(e=>console.error(e))
  }

  const closeModal = () => {
    toast.dismiss();
  }

  return (
    <section className={style.modal}>
      <h2>join room</h2>
      <div className={style.card}>
        <span>set interference</span>
        <div>
          <label>
            <input onClick={handleChange} type="radio" name="roomInterference" value="true" required />
            on
          </label>
          <label>
            <input onClick={handleChange} type="radio" name="roomInterference" value="false" required />
            off
          </label>
        </div>

      </div>
      <div className={style.btns}>
        <button onClick={joinRoom} >join</button>
        <button onClick={closeModal} >close</button>
      </div>
    </section>
  )
}