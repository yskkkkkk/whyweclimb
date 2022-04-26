import { useRouter } from "next/router"
import style from '../styles/waitRoom.module.css';

export default function WaitRoom() {

  const router = useRouter();
  const {roomID} = router.query;

  return (
    <main className={style.container}>
      <header>welcome to room: {roomID}</header>
      <section>
        3 / 4
      </section>
    </main>
  )
}