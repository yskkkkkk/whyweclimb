import style from './lobby.module.css';
import Link from 'next/link';

export default function Lobby({toggleJoinModal, toCreate, toggleFindModal}) {

  return (
    <>
      <nav className={style.lobby}>
        <h2><a href="#" onClick={toggleJoinModal} >join</a></h2>    {/* 웹소켓을 이용해야 자연스러운 대기화면 구현 가능, 고로 일단은 보류 */}
        <h2><a href="#" onClick={toggleFindModal} >find</a></h2>
        <h2><a href="#" onClick={toCreate} >create</a></h2>
      </nav>
      <Link href={'/'} passHref>
        <button>back</button>
      </Link> 
    </>
  )
}