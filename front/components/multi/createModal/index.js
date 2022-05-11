import style from './createModal.module.css';
import { useState } from 'react';
import axios from 'axios';
import toast from 'react-hot-toast';

export default function CreateModal() {
  const basicURL = 'https://k6a401.p.ssafy.io/api'
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

  const closeModal = () => {
    toast.dismiss();
  }

  function createRoom(){
    axios.post(`${basicURL}/chat/room`, roomInfo)
      .then(response => response.data)
      .then(data=>location.href=`multi/${data.roomCode}`)
      .catch(err=>console.error(err));
  }

  return (
    <main className={style.container}>
      <h2>Create room</h2>
      <div className={style.container2}>
        <section className={style.choice}>
          <span>Room Interference</span>
          <div className={style.buttons}>
            <label className={style.button}>
              <input onClick={handleChange} type="radio" name="roomInterference" value="true" required />
              on
            </label>
            <label className={style.button}>
              <input onClick={handleChange} type="radio" name="roomInterference" value="false" required />
              off
            </label>
          </div>
        </section>
        <section className={style.choice}>
          <span>Private Room</span>
          <div className={style.buttons}>
            <label className={style.button}>
              <input onClick={handleChange} type="radio" name="roomPrivate" value="true" required />
              yes
            </label>
            <label className={style.button}>
              <input onClick={handleChange} type="radio" name="roomPrivate" value="false" required />
              no
            </label>
          </div>
        </section>
        <section className={style.choice}>
          <span>Max player number</span>
          <div className={style.buttons}>
            <label className={style.button}>
              <input onClick={handleChange} type="radio" name="roomMaxNum" value="2" required />
              2
            </label>
            <label className={style.button}>
              <input onClick={handleChange} type="radio" name="roomMaxNum" value="3" required />
              3
            </label>
            <label className={style.button}>
              <input onClick={handleChange} type="radio" name="roomMaxNum" value="4" required />
              4
            </label>
          </div>
        </section>
      </div>
      <div className={style.links}>
        <button onClick={createRoom}>create room</button>
        <button onClick={closeModal}>back</button>
      </div>
    </main>
  )
}