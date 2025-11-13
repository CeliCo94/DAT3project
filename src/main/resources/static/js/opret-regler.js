(function () {
  const $ = (sel) => document.querySelector(sel);
  const show = (id) => document.getElementById(id).classList.remove('hidden');
  const hide = (id) => document.getElementById(id).classList.add('hidden');

  // inputs
  const inMin = $('#inMin'), inMax = $('#inMax');
  const outMin = $('#outMin'), outMax = $('#outMax');
  const flowMin = $('#flowMin'), flowMax = $('#flowMax');
  const dept = $('#dept'), apt = $('#apt');
  const desc = $('#desc');

  // buttons
  const btnTest = $('#btnTest');
  const btnBack = $('#btnBack');
  const btnActivate = $('#btnActivate');
  const btnEdit = $('#btnEdit');
  const btnSuspend = $('#btnSuspend');

  // charts + numbers
  const notifTotal = $('#notifTotal'), notifBadge = $('#notifBadge');
  const barDay1 = $('#barDay1'), barDay2 = $('#barDay2');
  const barDept = $('#barDept'), labelDept = $('#labelDept'), labelDeptCount = $('#labelDeptCount');

  // summary fields
  const sumIn = $('#sumIn'), sumOut = $('#sumOut'), sumFlow = $('#sumFlow');
  const sumLoc = $('#sumLoc'), sumDesc = $('#sumDesc'), sumNotif = $('#sumNotif');
  const statusBadge = $('#statusBadge');

  function calcNotifications() {
    // super simple “formula” for demo purposes
    const spanIn = (+inMax.value || 0) - (+inMin.value || 0);
    const spanOut = (+outMax.value || 0) - (+outMin.value || 0);
    const spanFlow = (+flowMax.value || 0) - (+flowMin.value || 0);
    let total = Math.max(0, Math.round((spanIn + spanOut + spanFlow) / 5));

    if (dept.value && dept.value.includes('A')) total += 6;
    if (apt.value) total += 2;

    return Math.min(total, 48); // clamp for nice bars
  }

  function fillBars(total) {
    // split across two days, and department
    const d1 = Math.max(1, Math.round(total * 0.1));
    const d2 = Math.max(1, total - d1);
    barDay1.style.height = Math.min(100, d1 * 4) + '%';
    barDay2.style.height = Math.min(100, d2 * 4) + '%';
    barDept.style.height = Math.min(100, total * 4) + '%';
    labelDept.textContent = dept.value || 'Afdeling';
    labelDeptCount.textContent = total.toString();
  }

  btnTest?.addEventListener('click', () => {
    const total = calcNotifications();
    notifTotal.textContent = total.toString();
    notifBadge.textContent = `${total} notifikationer`;
    fillBars(total);

    hide('editor'); show('test');
    statusBadge.textContent = 'Testet';
    statusBadge.classList.add('blue');
  });

  btnBack?.addEventListener('click', () => {
    show('editor'); hide('test'); hide('active');
    statusBadge.textContent = 'Udkast';
  });

  btnActivate?.addEventListener('click', () => {
    const total = parseInt(notifTotal.textContent || '0', 10);

    sumIn.value = `Min: ${inMin.value || '-'}°C | Max: ${inMax.value || '-'}°C`;
    sumOut.value = `Min: ${outMin.value || '-'}°C | Max: ${outMax.value || '-'}°C`;
    sumFlow.value = `Min: ${flowMin.value || '-'} m³ | Max: ${flowMax.value || '-'} m³`;
    sumLoc.value = `Afdeling: ${dept.value || '—'}${apt.value ? ` | Lejlighed: ${apt.value}` : ''}`;
    sumDesc.value = desc.value || '';
    sumNotif.textContent = total.toString();

    hide('editor'); hide('test'); show('active');
    statusBadge.textContent = 'Aktiv';
  });

  btnEdit?.addEventListener('click', () => {
    show('editor'); hide('test'); hide('active');
    statusBadge.textContent = 'Udkast';
  });

  btnSuspend?.addEventListener('click', () => {
    alert('Reglen er nu suspenderet (demo).');
    statusBadge.textContent = 'Udkast';
    show('editor'); hide('test'); hide('active');
  });
})();
