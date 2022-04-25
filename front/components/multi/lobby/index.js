import style from './lobby.module.css';

export default function Lobby({toWaiting, toCreate, toggleFindModal}) {

  return (
    <nav className={style.lobby}>
      <h2><a href="#" onClick={toWaiting} >join</a></h2>
      <h2><a href="#" onClick={toggleFindModal} >find</a></h2>
      <h2><a href="#" onClick={toCreate} >create</a></h2>
    </nav>
  )
}