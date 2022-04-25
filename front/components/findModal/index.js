import style from './findModal.module.css';

export default function FindModal({toggleFindModal}) {

  return (
    <section className={style.modal}>
      <div className={style.card}>
        <label>search room 
          <input type="text" required /></label>
      </div>
      <button onClick={toggleFindModal} >close</button>
    </section>
  )
}