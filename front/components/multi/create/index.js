import style from './create.module.css';
import { useState } from 'react';
import axios from 'axios'

export default function Create({toLobby}) {
  const basicURL = 'http://localhost:8081'
  const [roomName, setRoomName] = useState("");
  const [interference, setInterference] = useState("");
  const [privateRoom, setPrivateRoom] = useState("");
  const [playerNumber, setPlayerNumber] = useState("");
  const [roomInfo, setRoomInfo] = useState({roomName:"", interference:"", privateRoom:"", playerNumber:"", roomInfo:""});
  const nameChange = (e) => {
    setRoomName(e.target.value);
  }

  const interferenceSelect = (e) => {
    setInterference(e.target.value);
  };
  const privacySelect = (e) => {
    setPrivateRoom(e.target.value);
  };
  const playerNumberSelect = (e) => {
    setPlayerNumber(e.target.value);
  };
  // const createRoom = (e) => {
  //   e.preventDefault();
  //   // 웹소켓 연결 요청 or http 요청으로 방만들어주면 해당 방으로 router push
  // }

  function createRoom(){
    if(roomName==="") {
      alert("방 제목을 입력해 주세요!!");
    } else {
      var params = new URLSearchParams();
      params.append("name", roomName);
      axios.post(`${basicURL}/chat/room`, params)
        .then(response => {
          console.log(response);
        })
        .catch(err=>console.error(err));
    }
  }

  return (
    <main className={style.container}>
      <section className={style.choice}>
        <span>RoomName</span>
        <div>
          <input type="text" value={roomName} onChange={nameChange} />
        </div>
      </section>
      <section className={style.choice}>
        <span>Interference</span>
        <div>
          <label>
            <input onClick={interferenceSelect} type="radio" name="interference" value="on" required />
            on
          </label>
          <label>
            <input onClick={interferenceSelect} type="radio" name="interference" value="off" required />
            off
          </label>
        </div>
      </section>
      <section className={style.choice}>
        <span>Private Room</span>
        <div>
          <label>
            <input onClick={privacySelect} type="radio" name="privateRoom" value="yes" required />
            yes
          </label>
          <label>
            <input onClick={privacySelect} type="radio" name="privateRoom" value="no" required />
            no
          </label>
        </div>
      </section>
      <section className={style.choice}>
        <span>Max player number</span>
        <div>
          <label>
            <input onClick={playerNumberSelect} type="radio" name="playerNumber" value="2" required />
            2
          </label>
          <label>
            <input onClick={playerNumberSelect} type="radio" name="playerNumber" value="3" required />
            3
          </label>
          <label>
            <input onClick={playerNumberSelect} type="radio" name="playerNumber" value="4" required />
            4
          </label>
        </div>
      </section>
      <button onClick={createRoom}>create room</button>
      <button onClick={toLobby}>back</button>
    </main>
  )
}