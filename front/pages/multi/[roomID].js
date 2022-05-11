import { useEffect, useState } from "react";
import { useRouter } from "next/router"
import style from '../../styles/waitRoom.module.css';
import axios from 'axios'
import Link from 'next/link';
import SockJS from 'sockjs-client';
import dynamic from "next/dynamic";
import { Suspense } from "react";
// import Engine from '../../components/multiEngine'
const StompJS = require('@stomp/stompjs');
const Engine = dynamic(() => { return import('../../components/multiEngine')}, {ssr:false});

const basicURL = 'https://k6a401.p.ssafy.io/api';
// const basicURL = `http://localhost:8081/api`
const Stomp = StompJS.Stomp;
const stomp = Stomp.over(function(){
  return new SockJS(`${basicURL}/ws-stomp`);
})
stomp.reconnect_delay = 5000;

export default function WaitRoom() {
  const [isStart, setIsStart] = useState(true);
  const router = useRouter();
  const {roomID} = router.query;
  const [userInfo, setUserInfo] = useState();

  // function sendMessage(msg){
  //   console.log('hii');
  //   stomp.send(`/pub/chat/message`, {}, JSON.stringify({type:'MOVE', roomId:roomID, sender:'noman1', message:msg}));
  // }

  function startGame(){
    setIsStart(prev=>!prev);
  }

  // function receiveMessage(msg){
  //   console.log('msg',msg)
  //   setMsg(msg);
  // }

  // function socketConnect(){
  //   stomp.connect({},
  //     function(){
  //       stomp.subscribe(`/sub/chat/room/`+roomID, function(message){
  //         var recv = JSON.parse(message.body);
  //         receiveMessage(recv);
  //         console.log(message);
  //       });
  //       stomp.send(`/pub/chat/message`,{},JSON.stringify({type:'ENTER', roomCode:roomID, sender:"noman"}));
  //       // console.log('stomp',stomp);
  //     },
  //     function(error){
  //       console.log('error',error.headers.message);
  //     }
  //   )
  // }

  function getUserInfo(){
    const token = localStorage.getItem("token");
    const headers = {
      'Authorization': token,
      mode: 'no-cors'
    }
    fetch(`https://k6a401.p.ssafy.io/api/user/information`, {headers:headers})
      .then(res => res.json())
      .then(data => setUserInfo(data))
      .catch(err => console.log(err))
  }
  
  useEffect(()=>{
    if(roomID){
      axios.get(`${basicURL}/chat/room/${roomID}`)
        .then(res=>res.data)
        .then(data=>{
          if(data!==''){
            console.log(data);
            getUserInfo();
          } else {
            location.href="/multi";
          }
        })
    }
  },roomID)

  return (
    <>
      {/* {!isStart && <main className={style.container}>
          <header>welcome to room: {roomID}</header>
          <section>
            3 / 4
          </section>
          <Link href={'/multi'}>
            <button>back to Lobby</button>
          </Link>
          <button onClick={startGame}>Start Game</button>
      </main> }
      {isStart && <main className={style.container}>
        <div>
          <p>점프컹스</p>
          <p id="mute">Mute<input type="checkbox"/></p>
        </div>
          <Engine stomp={stomp} userInfo={userInfo} roomId={roomID}/>
      </main>} */}
      <main className={style.container}>
        <div className={style.head}>
          <p className={style.title}>Why We Climb</p>
          
            <p id="mute">Mute<input type="checkbox" /></p>
            <p className={style.time} id="time"></p>
          
        </div>
        <Engine stomp={stomp} roomId={roomID} userInfo={userInfo}/>
        <div className={style.buttons}>
          <Link href={'/'} passHref>
            <a><h3>Back</h3></a>
          </Link>
        </div>
      </main>
    </>
    
  )
}