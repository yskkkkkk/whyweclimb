import style from './findModal.module.css';
import axios from 'axios';
import toast from 'react-hot-toast';
import { useState } from 'react';
import {motion} from "framer-motion";


export default function FindModal({handleClose}) {
  const basicURL = 'https://k6a401.p.ssafy.io/api'
  // const basicURL = `http://localhost:8081/api`
  const [roomID, setRoomID] = useState('');

  const writeRoomID = (e) => {
    setRoomID(e.target.value);
  };

  const findRoom = () => {
    axios.get(`${basicURL}/chat/room/${roomID}`)
      .then(res=>res.data)
      .then(data=>{
        if(data !== ""){
          switch(data.roomFindResult) {
            case 'ok':
              location.href=`/multi/${data.roomCode}`;
              break;
            case 'full':
              toast.error("the room is full..");
              break;
            case 'start':
              toast("here we go!", {icon: "🎮"});
              break;
            default:
              toast.error("the room doesn't exist. Please check your room ID.");
          }
        } else {
          toast.error("the room doesn't exist. Please check your room ID.");
        }
        console.log(data);
      })
      .catch(err=>console.error(err))
  }

  const popUp = {
    initial: {
      y: "-30vh",
      opacity: 0,
    },
    visible: {
      y: "0",
      opacity: 1,
      transition: {
        duration: 0.1,
        type: "spring",
        damping: 25,
        stiffness: 500,
      }
    },
    exit: {
      y: "-30vh",
      opacity: 0,
      transition: {
        duration: 0.2,
      }
    }
  }

  return (
    <motion.div
      onClick={(e) => e.stopPropagation()}
      variants={popUp}
      initial="initial"
      animate="visible"
      exit="exit"
    >
      <section className={style.modal}>
        <h2>Find room</h2>
        <div className={style.card}>
          <label>search room 
            <input type="text" required onChange={writeRoomID} /></label>
        </div>

        <div className={style.btns}>
          <button className={style.okBtn} onClick={findRoom} >join</button>
          <button className={style.backBtn} onClick={handleClose} >close</button>
        </div>
      </section>
    </motion.div>
  )
}