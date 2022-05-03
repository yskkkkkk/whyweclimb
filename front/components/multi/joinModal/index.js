import {useRouter} from 'next/router';
import { useState, useEffect } from 'react';
import style from './joinModal.module.css';
import axios from 'axios'


export default function JoinModal({toggleJoinModal}) {
  const basicURL = 'http://localhost:8081/api'
  const [isInterference, setIsInterference] = useState("");

  const handleChange = (e) => {
    setIsInterference(e.target.value);
  }

  // const findRoom = () => {                       // 찾는 방이 있으면 해당 페이지로 route. 다만 여기서 ws으로 바로 연결할지는 미정.
  //   fetch(`https://${roomID}/exists`)
  //     .then((response) => response.json())
  //     .then((data) => {
  //       if (data) {
  //         router.push(`/multi/${roomID}`);
  //       }
  //       else {
  //         alert('invalid RoomID');
  //       }
  //     });
  // };

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

  return (
    <section className={style.modal}>
      <div className={style.card}>
      <span>roomInterference</span>
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
        <button onClick={toggleJoinModal} >close</button>
      </div>
    </section>
  )
}