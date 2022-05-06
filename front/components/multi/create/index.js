import style from './create.module.css';
import { useState } from 'react';
import axios from 'axios';

export default function Create({toLobby}) {
  const basicURL = 'http://localhost:8081/api'
  const [roomCode, setroomCode] = useState("");
  const [roomInterference, setroomInterference] = useState("");
  const [roomPrivate, setroomPrivate] = useState("");
  const [roomMaxNum, setroomMaxNum] = useState("");
  const [roomInfo, setRoomInfo] = useState({roomCode:"", roomroomInterference:"", roomPrivate:"", roomMaxNum:""});
  const nameChange = (e) => {
    setroomCode(e.target.value);
  }

  const roomInterferenceSelect = (e) => {
    setroomInterference(e.target.value);
  };
  const privacySelect = (e) => {
    setroomPrivate(e.target.value);
  };
  const roomMaxNumSelect = (e) => {
    setroomMaxNum(e.target.value);
  };
  const handleChange = (e) => {
    const {name, value} = e.target
    // if (name !== "roomMaxNum") {
    //   setRoomInfo(prev => ({
    //     ...prev,
    //     [name]: value === "true" ? true : false
    //   })        
    //   )    
    if (name === "roomMaxNum") {
      setRoomInfo(prev => ({
        ...prev,
        [name]: parseInt(value)
      }))
    } else if (name === "roomCode") {
      setRoomInfo(prev => ({
        ...prev,
        [name]: value
      }))
    } else {
      setRoomInfo(prev => ({
        ...prev,
        [name]: value === "true" ? true: false
      }))
    }
  }


  function createRoom(){
    if(roomInfo.roomCode==="") {
      alert("방 제목을 입력해 주세요!!");
    } else {
      const data = JSON.stringify(roomInfo);
      axios.post(`${basicURL}/chat/room`, roomInfo)
        .then(response => response.data)
        .then(data=>location.href=`multi/${data.roomCode}`)
        .catch(err=>console.error(err));
    }
  }

  return (
    <main className={style.container}>
      <section className={style.choice}>
        <span>roomCode</span>
        <div>
          <input type="text" name="roomCode" value={roomInfo.roomCode} onChange={handleChange} />
        </div>
      </section>
      <section className={style.choice}>
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
      </section>
      <section className={style.choice}>
        <span>Private Room</span>
        <div>
          <label>
            <input onClick={handleChange} type="radio" name="roomPrivate" value="true" required />
            yes
          </label>
          <label>
            <input onClick={handleChange} type="radio" name="roomPrivate" value="false" required />
            no
          </label>
        </div>
      </section>
      <section className={style.choice}>
        <span>Max player number</span>
        <div>
          <label>
            <input onClick={handleChange} type="radio" name="roomMaxNum" value="2" required />
            2
          </label>
          <label>
            <input onClick={handleChange} type="radio" name="roomMaxNum" value="3" required />
            3
          </label>
          <label>
            <input onClick={handleChange} type="radio" name="roomMaxNum" value="4" required />
            4
          </label>
        </div>
      </section>
      <button onClick={createRoom}>create room</button>
      <button onClick={toLobby}>back</button>
    </main>
  )
}