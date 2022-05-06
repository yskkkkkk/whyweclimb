import Lobby from '../components/multi/lobby';
import FindModal from '../components/multi/findModal';
import JoinModal from "../components/multi/joinModal";
import Create from '../components/multi/create';
import style from '../styles/multi.module.css';
import Link from 'next/link';
import { useState } from 'react';
import axios from 'axios';

export default function Multi() {
  const basicURL = 'http://localhost:8081/api'
  const [multiRoom, setMultiRoom] = useState(0);  // 0:lobby  1:create
  const [findModal, setFindModal] = useState(false);
  const [joinModal, setJoinModal] = useState(false);

  const toLobby = () => {
    setMultiRoom(0);
  };
  const toCreate = () => {
    setMultiRoom(1);
  };
  const toggleFindModal = () => {
    setFindModal(!findModal);
  }

  const toggleJoinModal = () => {
    setJoinModal(!joinModal);
  }

  // const joinRoom = () => {          // 방 들어가지는 로직 정해야함. 가장 먼저 만들어진 순서로 넣을지, 가장 사람이 많은 순서로 넣을지, http request로 방이 있으면 일단 들어가지게할지 ws으로 인원이 다 모이면 하나의 방으로 들어가서 바로 시작하게 할지(롤처럼).
  //   fetch(`https://rooms`)
  //   .then((response) => response.json())
  //   .then((data) => {
  //     if (data) {
  //       router.push(`/${data[0]}`);
  //     }
  //     else {
  //       alert('no rooms to join');
  //     }
  //   });
  // }

  const joinRoom = () => {
    axios.get(`${basicURL}/chat/rooms`)
      .then(res=>res.data)
      .then(data=>{
        if(data.length === 0) {
          alert("대기 중인 방이 없습니다. 잠시후 시도해 주세요.");
        } else {
          location.href=`multi/${data[data.length-1].roomId}`;
        }
      })
      .catch(e=>console.error(e))
  }

  return (
    <main className={findModal ? style.modalOn : style.multi}>

      {multiRoom == 0 && <Lobby toggleJoinModal={toggleJoinModal} toCreate={toCreate} toggleFindModal={toggleFindModal} />}

      {multiRoom == 1 && <Create toLobby={toLobby} />}

      {findModal && <FindModal toggleFindModal={toggleFindModal} />} 

      {joinModal && <JoinModal toggleJoinModal={toggleJoinModal} />}
        
    </main>
  )
}