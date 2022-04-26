import Lobby from '../components/multi/lobby';
import FindModal from '../components/findModal';
import Create from '../components/create';
import style from '../styles/multi.module.css';
import Link from 'next/link';
import { useState } from 'react';

export default function Multi() {
  
  const [multiRoom, setMultiRoom] = useState(0);  // 0:lobby  1:waiting  2:create
  const [findModal, setFindModal] = useState(false);

  const toLobby = () => {
    setMultiRoom(0);
  };
  const toWaiting = () => {
    setMultiRoom(1);
    // joinRoom();
  };
  const toCreate = () => {
    setMultiRoom(2);
  };
  const toggleFindModal = () => {
    setFindModal(!findModal);
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

  return (
    <main className={findModal ? style.modalOn : style.multi}>

      {multiRoom == 0 && <Lobby toWaiting={toWaiting} toCreate={toCreate} toggleFindModal={toggleFindModal} />}

      {multiRoom == 2 && <Create toLobby={toLobby} />}

      {findModal && <FindModal toggleFindModal={toggleFindModal} />}

      <Link href={'/'} passHref>
        <button>back</button>
      </Link>      
        
    </main>
  )
}