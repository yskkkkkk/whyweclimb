
import FindModal from '../components/multi/findModal';
import JoinModal from "../components/multi/joinModal";
import CreateModal from '../components/multi/createModal';
import style from '../styles/multi.module.css';
import Link from 'next/link';
import { Parallax, ParallaxLayer } from '@react-spring/parallax';
import toast from 'react-hot-toast';

export default function Multi() {
  // const basicURL = 'https://k6a401.p.ssafy.io/api'

  const toggleCreateModal = () => {
    toast(<CreateModal />)
  }
  const toggleFindModal = () => {
    toast(<FindModal />)
  }
  const toggleJoinModal = () => {
    toast(<JoinModal />)
  }

  // const joinRoom = () => {
  //   axios.get(`${basicURL}/chat/rooms`)
  //     .then(res=>res.data)
  //     .then(data=>{
  //       if(data.length === 0) {
  //         alert("대기 중인 방이 없습니다. 잠시후 시도해 주세요.");
  //       } else {
  //         location.href=`multi/${data[data.length-1].roomId}`;
  //       }
  //     })
  //     .catch(e=>console.error(e))
  // }

  return (
    <Parallax 
      pages={1}
      style={{
        overflow: "hidden",
      }}
    >
      <ParallaxLayer
        offset={0}
        style={{
          backgroundImage: 'url("/images/stars.svg")',
          backgroundColor: '#565656',
          backgroundSize: 'cover',
          display: 'flex',
          justifyContent: 'center',
          zIndex: '0',
        }}
      />

      {/* <ParallaxLayer offset={0} speed={0.2} style={{ opacity: 0.1 }}>
        <img src='/images/intro.svg' style={{ width: '100%'}} />
      </ParallaxLayer> */}

      <ParallaxLayer
        offset={0}
        factor={4}
        style={{
          backgroundImage: 'url("/images/intro.svg")',
          backgroundSize: 'cover',
          position: 'absolute',
          left: '50%',
          top: '-110%',
          opacity: '0.2',
          transform: 'translate(-50%, -50%)',
          zIndex: '0',
        }}
      />

      <ParallaxLayer offset={0} style={{zIndex: 6}} >
      <main className={style.multi}>
        <nav className={style.lobby}>
          <div className={style.joinBtn} onClick={toggleJoinModal}>
            <div>
              <div className={style.joinImg1} />
              <div className={style.joinImg2} />
              <div className={style.spark} />
            </div>
            <h2>join</h2>
          </div>
          <div className={style.findBtn} onClick={toggleFindModal}>
            <div>
              <div className={style.findImg}>
                <div className={style.blingSS} />
                <div className={style.blingS} />
                <div className={style.blingM} />
              </div>
            </div>
            <h2>find</h2>
          </div>
          <h2><a href="#" onClick={toggleCreateModal} >create</a></h2>
        </nav>
        <Link href={'/'} passHref>
          <button className={style.back} >back</button>
        </Link>
      </main>
      </ParallaxLayer>
    </Parallax>
  )
}