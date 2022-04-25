import style from './lobby.module.css';

export default function Lobby() {

  return (
    <main className={style.lobby}>
      <h2><a href="#" >join</a></h2>
      <h2><a href="#" >find</a></h2>
      <h2><a href="#" >create</a></h2>
    </main>
  )
}